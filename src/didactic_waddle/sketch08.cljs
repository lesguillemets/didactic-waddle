(ns didactic-waddle.sketch08
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(def width 500)
(def height 500)
(def dz 3)
(def mlt 1)
(def normal-length 30)

(defrecord Branch [start θ φ r end])

(defn end-point [start θ φ r]
  (let [[x y z] start
        rxy (* r (Math/sin φ)) ]
    [(+ x (* rxy (Math/cos θ)))
     (+ y (* rxy (Math/sin θ)))
     (+ z (Math/abs (* r (Math/cos φ))))
     ]))

(defn mkbranch [start θ φ r]
  (->Branch start θ φ r (end-point start θ φ r)))
; (defn branch [& args]
;   (apply ->Branch (conj args (apply end-point args))))

(defn rand-branch-from [r start]
  (mkbranch start
            (* 2 Math/PI (rand))
            (* (/ 1 2) Math/PI (Math/pow (rand) 2))
            r))

(defn grow-next [r n br]
  (repeatedly
    n
    #( rand-branch-from r (:end br))))

(defn rand-branch [r xmax ymax]
  (rand-branch-from r [(rand-int xmax) (rand-int ymax) 0]))

(defn rand-branch-initial [r xmax ymax]
  (let [xhalf (/ xmax 3)
        yhalf (/ ymax 3)
        xrem (/ (- xmax xhalf) 2)
        yrem (/ (- ymax yhalf) 2)
        ]
  (rand-branch-from r [(+ xrem (* (rand) xhalf)) (+ yrem (* (rand) yhalf)) 0])))
(defn setup []
  (q/color-mode :rgb)
  (q/frame-rate 30)
  (q/background 0)
  (q/no-stroke)
  (enable-console-print!)
  {:z 0 :edges (repeatedly 5 #(rand-branch-initial 10 width height))})

(defn update-edges [z r n edges]
  (defn alive? [edge]
    (> (nth (:end edge) 2) z))
  (let [es (group-by alive? edges)
        alives (get es true)
        deads (get es false)
        newborn (apply concat (map (fn [e] (grow-next r (rand-int n) e))  deads))
        ]
  (concat alives newborn)))

(defn update-state [s]
  (let [newz (+ (:z s) dz)]
    {:z newz
     :edges (update-edges newz normal-length 4 (:edges s)) }))

(def blue [0 255 75])

(defn draw-dot [size x y c]
  (apply q/fill c)
  (q/rect (* mlt x) (* mlt y) (* size mlt) (* size mlt)))

(defn random-dot [x y]
  [(rand x) (rand y)])

(defn section-at [p z]
  (let [[sx sy sz] (:start p)
        [ex ey ez] (:end p)
        rat (/ (- z sz) (- ez sz))
        ]
    [ (+ sx (* rat (- ex sx)  ))
      (+ sy (* rat (- ey sy)  )) ]))

(defn draw-state [s]
  (q/background 0)
  (doseq [p (:edges s)]
    (let [[x y] (section-at p (:z s) )]
      (draw-dot 1 x y [255 255 255]))))

(q/defsketch didactic-waddle
  :host "sketch08"
  :size [(* mlt width) (* mlt height)]
  :setup setup
  :update update-state
  :draw draw-state
  :middleware [m/fun-mode])

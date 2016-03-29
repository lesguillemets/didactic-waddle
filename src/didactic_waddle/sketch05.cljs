(ns didactic-waddle.sketch05
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(def width 100)
(def height 100)
(def expand 4)
(def neighbour-width 1)
(def hue 100)
(def sat 200)
(def max-b 200)
(def area (* width height))
(def sq (fn [n] (* n n)))

(defn random-field [w h]
  (vec
    (for [y (range h)]
      (vec
        (for [x (range w)]
          (rand-int 2))))))

(defn neighbouring [x y]
  (for [dx (range (- neighbour-width) (inc neighbour-width))
        dy (range (- neighbour-width) (inc neighbour-width))
        :when (not (and (= dx 0) (= dy 0)))]
    [dx dy]))

(defn add-weight [v dx dy] v)

(defn get-neighbours [field x y]
  (map (fn [[dx dy]]
         (add-weight (get-in field [(+ y dy) (+ x dx)] 0) dx dy))
       (neighbouring x y)))

(defn check-spawn [v neighbours]
  (if (= v 1)
    (cond
      (< neighbours 2) 0
      (< neighbours 4) 1
      :else 0)
    (cond
      (= neighbours 3) 1
      :else 0)))


(defn next-field [field]
  (vec
    (for [y (range height)]
      (vec
        (for [x (range width)]
          (let [v (apply + (get-neighbours field x y))]
            (check-spawn (get-in field [y x] 0) v)))))))

(defn update-state [s]
  (assoc s :field (next-field (:field s))))

(defn setup []
  (q/color-mode :hsb)
  (q/frame-rate 2)
  (q/background 0)
  (enable-console-print!)
  (q/no-stroke)
  ; (q/no-loop)
  {:field (random-field width height)})

(defn to-color [value]
  [hue sat (* max-b value)])

(defn draw-dot-at [field x y]
  (let [color (to-color (get-in field [y x]))]
    (apply q/fill color) ; with-stroke doesn't work somehow
    (q/rect (* expand x) (* expand y) expand expand)
    ))

(defn draw-state [s]
  (q/background 0)
  (doseq [y (range height)]
    (doseq [x (range width)]
      (draw-dot-at (:field s) x y)))
  )


(q/defsketch didactic-waddle
  :host "didactic-waddle"
  :size [(* expand width) (* expand height)]
  :setup setup
  :update update-state
  :draw draw-state
  :middleware [m/fun-mode])

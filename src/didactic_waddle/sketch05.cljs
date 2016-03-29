(ns didactic-waddle.sketch05
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(def width 250)
(def height 250)
(def hue 100)
(def sat 200)
(def max-b 200)
(def area (* width height))
(def sq (fn [n] (* n n)))

(defn update-state [s] s)
(defn setup []
  (q/color-mode :hsb)
  (q/frame-rate 30)
  (q/background 0)
  (q/no-loop)
  {:field
   (vec
     (for [y (range height)]
       (vec
         (for [x (range width)]
           (rand)))))
   })

(defn to-color [value]
  [hue sat (* max-b value)])

(defn draw-dot-at [x y field]
  (let [color (to-color (nth (nth field y) x))]
    (apply q/stroke color) ; with-stroke doesn't work somehow
    (q/point x y)
    ))

(defn draw-state [s]
  (q/background 0)
  (doseq [y (range height)]
    (doseq [x (range width)]
      (draw-dot-at x y (:field s))))
  )


(q/defsketch didactic-waddle
  :host "didactic-waddle"
  :size [width height]
  :setup setup
  :update update-state
  :draw draw-state
  :middleware [m/fun-mode])

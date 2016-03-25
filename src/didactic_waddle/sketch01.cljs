(ns didactic-waddle.sketch01
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(def update-state identity)
(def size 500)
(def dist 10)
(def TwoPI (* Math/PI 2))

(defn setup []
  (q/frame-rate 60)
  (q/color-mode :hsb)
  (q/no-loop)
  (map (fn [n] {:x (* dist (mod n 51)),
                :y (* dist (quot n 51))})
       (range 2750)))

(defn draw-circle [circ]
  (q/no-stroke)
  (let [x (:x circ)
        y (:y circ)
        noise (q/noise (/ x 300) (/ y 300))
        radius (* (Math/sqrt noise) dist 1.2)
        ]
    (q/fill (* 255 noise) 200 200)
    (q/arc x y radius radius 0 TwoPI)))

(defn draw-state [state]
  (q/background 250)
  (doseq [circ state] (draw-circle circ)))

(q/defsketch didactic-waddle
  :host "didactic-waddle"
  :size [size size]
  :setup setup
  :update update-state
  :draw draw-state
  :middleware [m/fun-mode])

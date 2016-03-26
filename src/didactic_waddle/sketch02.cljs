(ns didactic-waddle.sketch02
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(def size 500)
(def TwoPI (* Math/PI 2))

(defn setup []
  (q/frame-rate 30)
  (q/color-mode :hsb)
  ; (q/no-loop)
  {:x 20 :y 20 :r 40 :step 0})

(defn draw-circle [circ]
  (q/no-stroke)
  (let [x (:x circ)
        y (:y circ)
        radius (* (q/noise (/ (:step circ) 30)) (:r circ))]
    (q/arc x y radius radius 0 TwoPI)))

(defn update-state [{x :x y :y step :step :as c}]
  (assoc c
         :x (+ (* (Math/sqrt (q/noise (/ step 30))) 2) x),
         :y (+ (* (Math/sqrt (q/noise (/ step 30))) 2) y),
         :step (inc step)
         ))

(defn draw-state [c]
  ; (q/background 0 20) "Transparency can't be used"
  (q/fill 0 0 0 5)
  (q/rect 0 0 size size)
  (q/fill 120 240 240)
  (draw-circle c))

(q/defsketch didactic-waddle
  :host "didactic-waddle"
  :size [size size]
  :setup setup
  :update update-state
  :draw draw-state
  :middleware [m/fun-mode])

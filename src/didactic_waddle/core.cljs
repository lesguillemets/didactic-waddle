(ns didactic-waddle.core
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(defn setup []
  (q/frame-rate 60)
  ; Set color mode to HSB (HSV) instead of default RGB.
  (q/color-mode :hsb)
  {:color 0,
   :size  1,
   :growing true,
   :angle 0
   })

(defn update-state [s]
  (let [size (:size s)
        growing (:growing s)]
    {:color (mod (+ (:color s) 0.7) 255),
     :angle (mod (+ (:angle s) 0.02) (* 2 Math/PI)),
     :size (+ size
              (if growing 0.01 -0.01)),
     :growing (if growing
                (if (< 1.5 size) false true)
                (if (< size 0.5) true false))}))

(defn draw-state [state]
  ; Clear the sketch by filling it with light-grey color.
  (q/background 240)
  ; Set circle color.
  (q/stroke (:color state) 255 255)
  (let [size (* 200 (:size state))
        centre (/ (q/width) 2)]
    (q/rect-mode :center)
    (q/with-translation [centre centre]
      (q/with-rotation [(:angle state)]
        (q/rect 0 0 size size)))))

(q/defsketch didactic-waddle
  :host "didactic-waddle"
  :size [500 500]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update-state is called on each iteration before draw-state.
  :update update-state
  :draw draw-state
  ; This sketch uses functional-mode middleware.
  ; Check quil wiki for more info about middlewares and particularly
  ; fun-mode.
  :middleware [m/fun-mode])

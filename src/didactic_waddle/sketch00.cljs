(ns didactic-waddle.sketch00
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))
(defn make-box [x y base-size]
  {:color 0,
   :size  1,
   :growing true,
   :angle 0,
   :x x, :y y,
   :base-size base-size})

(defn next-stage [box]
  (let [size (:size box)
        growing (:growing box)]
    (assoc box
           :color (mod (+ (:color box) 0.4) 255),
           :angle (mod (+ (:angle box) 0.02) (* 2 Math/PI)),
           :size (+ size
                    (if growing 0.01 -0.01)),
           :growing (if growing
                      (if (< 1.8 size) false true)
                      (if (< size 0.5) true false)))))

(defn update-state [s] {:boxes (map next-stage (:boxes s))})


(defn setup []
  (q/frame-rate 60)
  ; Set color mode to HSB (HSV) instead of default RGB.
  (q/color-mode :hsb)
  {:boxes (map
            (fn [n]
              (nth (iterate next-stage
                            (make-box (* 30 (mod n 18))
                                      (* 30 (quot n 18))
                                      20))
                   n))

            (range 324))})

(defn draw-box [box]
  (q/stroke (:color box) 255 255)
  (let [size (* (:base-size box) (:size box))]
    (q/rect-mode :center)
    (q/with-translation [(:x box) (:y box)]
      (q/with-rotation [(:angle box)]
        (q/rect 0 0 size size)))))

(defn draw-state [state]
  ; Clear the sketch by filling it with light-grey color.
  (q/background 240)
  (doseq [box (:boxes state)] (draw-box box)))

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

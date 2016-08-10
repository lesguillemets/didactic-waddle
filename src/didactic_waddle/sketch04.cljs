(ns didactic-waddle.sketch04
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(def size 500)
(def grid-size 20)
(def grid-number (inc (/ size grid-size)))
(def n-of-radiations 36)
(def TwoPI (* Math/PI 2))

(defn setup []
  (q/background 0)
  (q/frame-rate 30)
  (q/color-mode :hsb)
  (q/rect-mode :center)
  (q/stroke 120 255 155)
  ; (q/no-loop)
  0
  )

(def update-state inc)

(defn random-draw [radius x y n]
  (let
    [noise (q/noise (/ x grid-size 30)
                    (/ y grid-size 30)
                    (/ n 30)
                    ),
     freq (Math/floor (* n-of-radiations noise)),
     dirs (take freq
                (map #(* Math/PI 2 % )
                (repeatedly #(rand))))]
    (q/stroke (Math/floor (* 256 noise)) 255 155)
    (q/fill (Math/floor (* 256 noise)) 55 255)
    (q/arc x y radius radius 0 TwoPI)
    (doseq [th dirs]
      (q/line x y
              (+ x (* 0.5 radius (Math/cos th)))
              (+ y (* 0.5 radius (Math/sin th)))))))

(defn draw-state [state]
  (q/background 0)
  (doseq [y (range 0 grid-number)]
    (doseq [x (range 0 grid-number)]
      (let
        [ x-loc (* grid-size (+ x (if (even? y) 0 0.5)))
          y-loc (* grid-size y)]
        (random-draw grid-size x-loc y-loc state)))))

(q/defsketch didactic-waddle
  :host "sketch04"
  :size [size size]
  :setup setup
  :update update-state
  :draw draw-state
  :middleware [m/fun-mode])

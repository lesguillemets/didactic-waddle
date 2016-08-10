(ns didactic-waddle.sketch03
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(def size 500)
(def grid-size 10)
(def grid-number (inc (/ size grid-size)))
(def n-of-radiations 1)
(def TwoPI (* Math/PI 2))

(defn setup []
  (q/background 0)
  (q/frame-rate 3)
  (q/color-mode :hsb)
  (q/rect-mode :center)
  (q/stroke 120 255 155)
  ; (q/no-loop)
  )

(defn update-state [s] s)

(defn random-draw [radius x y]
  (let
    [dirs (take n-of-radiations
                (map #(* (/ Math/PI 3) % )
                (repeatedly #(rand-int 6))))]
    (doseq [th dirs]
      (q/line x y
              (+ x (* radius (Math/cos th)))
              (+ y (* radius (Math/sin th)))))))

(defn draw-state [state]
  (q/background 0)
  (doseq [y (range 0 grid-number)]
    (doseq [x (range 0 grid-number)]
      (let
        [ x-loc (* grid-size (+ x (if (even? y) 0 0.5)))
          y-loc (* grid-size y)]
        (random-draw grid-size x-loc y-loc)))))

(q/defsketch didactic-waddle
  :host "sketch03"
  :size [size size]
  :setup setup
  :update update-state
  :draw draw-state
  :middleware [m/fun-mode])

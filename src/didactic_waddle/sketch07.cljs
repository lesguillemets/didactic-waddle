(ns didactic-waddle.sketch07
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))
(def width 250)
(def height 250)
(def r 0.03)
(def dt 0.05)
(def mlt 2)

(defn setup []
  (q/color-mode :hsb)
  (q/frame-rate 30)
;   (q/rect-mode :center)
  (q/background 0)
  (enable-console-print!)
  (q/no-stroke)
  0)

(defn update-state [t] (+ t dt))
(def cam-green [120 31 44])
(def cam-light-brown [45 31 75])
(def cam-dark-brown [0 19 19])

(defn to-color [v]
  (cond
    (< v 0.4) cam-light-brown
    (< v 0.5) cam-dark-brown
    :else cam-green))

(defn draw-dot [x y v]
  (apply q/fill-float (to-color v))
  (q/rect (* mlt x) (* mlt y) mlt mlt))

(defn draw-state [t]
  (let [noise-at (fn [x y] (q/noise (* r x) (* r y) t))]
    (doseq [x (range width)
            y (range height)]
      (draw-dot x y (noise-at x y)))
    ))

(q/defsketch didactic-waddle
  :host "sketch07"
  :size [(* mlt width) (* mlt height)]
  :setup setup
  :update update-state
  :draw draw-state
  :middleware [m/fun-mode])

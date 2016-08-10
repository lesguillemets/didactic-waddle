(ns didactic-waddle.sketch02
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(def size 500)
(def TwoPI (* Math/PI 2))

(defn gen-circle [n]
  (let [seed (q/random 0 2048)]
    {:x (* 20 n) :y (* 20 (- n)) :r 40 :step 0
     :noiser (fn [& x] (do
                       (q/noise-seed seed)
                       (apply q/noise x)))})) 

(defn setup []
  (q/background 0)
  (q/frame-rate 30)
  (q/color-mode :hsb)
  ; (q/no-loop)
  (map gen-circle (range -15 15)))

(defn draw-circle [circ]
  (q/no-stroke)
  (let [x (:x circ)
        y (:y circ)
        radius (* ((:noiser circ) (/ (:step circ) 30)) (:r circ))]
    (q/arc x y radius radius 0 TwoPI)))

(defn update-circle [{x :x y :y step :step f :noiser :as c}]
  (assoc c
         :x (+ (* (Math/sqrt (f (/ step 30))) 2) x),
         :y (+ (* (Math/sqrt (f (/ step 30))) 2) y),
         :step (inc step)
         ))

(defn update-state [s]
  (map update-circle s))

(defn draw-state [state]
  ; (q/background 0 20) "Transparency can't be used"
  (q/fill 0 0 0 3)
  (q/rect 0 0 size size)
  (q/fill 120 240 240)
  (doseq [c state] (draw-circle c)))

(q/defsketch didactic-waddle
  :host "sketch02"
  :size [size size]
  :setup setup
  :update update-state
  :draw draw-state
  :middleware [m/fun-mode])

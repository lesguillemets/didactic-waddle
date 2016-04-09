(ns didactic-waddle.sketch06
  (:require [quil.core :as q :include-macros true]
            [quil.middleware :as m]))

(def width 60)
(def height 60)
(def expand 8)
(def draw-size 20)
(def hue 100)
(def sat 200)
(def history-length 100)
(def alpha-step (/ 256 history-length))
(def size-step (/ draw-size history-length))
(defn random-move []
  (let [choise (rand-int 4)]
    (case choise
      0 [-1 0]
      1 [0 1]
      2 [1 0]
      3 [0 -1])))
(defn add-vect [[x0 y0] [x1 y1]]
  [(+ x0 x1) (+ y0 y1)])

(defn update-state [s]
  (let
    [next-loc (add-vect (:loc s) (random-move))
     next-hist (cons {:loc next-loc :age 0}
                     (filter #(< (:age %1) history-length)
                             (map #(assoc %1 :age (inc (:age %1)))
                                  (:history s))))]

    {:loc next-loc :history next-hist}))
    

(defn setup []
  (q/color-mode :hsb)
  (q/frame-rate 30)
  (q/rect-mode :center)
  (q/background 0)
  (enable-console-print!)
  (q/no-stroke)
  ; (q/no-loop)
  {:loc [0 0],
   :history []})

(defn to-alpha [age]
  (- 255 (Math/floor (* alpha-step age))))

(defn to-color [age]
  [ (Math/floor (* (/ 255 history-length) age)) 255 200 (to-alpha age)])

(defn to-size [age]
  (*  age size-step))
(defn draw-state [s]
  (q/background 0)
  (doseq [{[x0 y0] :loc, age :age} (:history s)]
    (apply q/fill (to-color age))
    (let [
          x (* expand (+ x0 (/ width 2)))
          y (* expand (+ y0 (/ height 2)))
          size (to-size age)
          ]
      (q/ellipse x y size size)
      )))


(q/defsketch didactic-waddle
  :host "didactic-waddle"
  :size [(* expand width) (* expand height)]
  :setup setup
  :update update-state
  :draw draw-state
  :middleware [m/fun-mode])

;; Copyright ©️ Rachel Bowyer 2016. All rights reserved.
;;
;; This program and the accompanying materials
;; are made available under the terms of the Eclipse Public License v1.0
;; which accompanies this distribution, and is available at
;; http://www.eclipse.org/legal/epl-v10.html
;;
;;; The Quil rendering code was inspired by http://mjg123.github.io/julia/index.html
;;; and http://stackoverflow.com/questions/16500656/which-color-gradient-is-used-to-color-mandelbrot-in-wikipedia


(ns csl-10-vector-public.vector-of-mandelbrot
  (:require [quil.core :as q]
            [quil.middleware :as m]))


;; Start example

(def max-iterations 99) ; zero based

(defn calc-mandelbrot [c-re c-im]                                         ; <1>
  (let [sq    (fn [x] (* x x))
        iter  (reduce (fn [[z-re z-im] i]
                        (if (or (= i 99) (> (+ (sq z-re) (sq z-im)) 4))
                          (reduced i)
                          [(+ c-re (sq z-re) (- (sq z-im)))
                           (+ c-im (* 2 z-re z-im))]))
                      [0 0] (range (inc max-iterations)))]
        (vector-of :double c-re c-im iter)))                              ; <2>

(def mandelbrot-set                                                       ; <3>
  (for [im (range 1 -1 -0.05) re (range -2 0.5 0.0315)]
    (calc-mandelbrot re im)))

(doseq [row (partition 80 mandelbrot-set)]                                ; <4>
  (doseq [point row]
    (print (if (> max-iterations (get point 2)) "*" " ")))
  (println))


;; End example


;;; Very simple rendering code to produce high resolution mandlebrot set for
;;; the image in the book

(def ^:private width 600)

(def ^:private height 600)

(defn setup []
  (q/frame-rate 50)
  (q/color-mode :rgb))


;; re ∈ [-2.25, 0.75], im ∈ [-1, 1]

(defn pt-to-plane [x y w h]
  [(- (* (/ (double x) w) 3.0) 2.25)
   (- (* (/ (double y) h) 3.0) 1.5)])

; Default Ultra Fractal colour mapping
(def colour-map [[66  30  15]  ;# brown 3
                 [25   7  26]  ;# dark violett
                 [9   1  47]   ;# darkest blue
                 [4   4  73]   ;# blue 5
                 [0   7 100]   ;# blue 4
                 [12  44 138]  ;# blue 3
                 [24  82 177]  ;# blue 2
                 [57 125 209]  ;# blue 1
                 [134 181 229] ;# blue 0
                 [211 236 248] ;# lightest blue
                 [241 233 191] ;# lightest yellow
                 [248 201  95] ;# light yellow
                 [255 170   0] ;# dirty yellow
                 [204 128   0] ;# brown 0
                 [153  87   0] ;# brown 1
                 [106  52   3] ;# brown 2
                 ])

(defn rgb-of [v]
  (if (= v max-iterations)
    (q/color 0 0 0)
    (apply q/color (colour-map (mod (int v) 16)))))

(defn draw []
    (let [w (q/width)
          h (q/height)]
      (doseq [x (range w)
              y (range h)]
        (let [[re im] (pt-to-plane x y w h)
              v (get (calc-mandelbrot re im) 2)]
          (q/stroke (rgb-of v))
          (q/point x y)))))

(defn show-image []
  (q/defsketch mandelbrot
               :title "Mandelbrot"
               :setup setup
               :draw draw
               :renderer :p2d
               :size [width height]))



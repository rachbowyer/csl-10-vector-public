;; Copyright ©️ Rachel Bowyer 2016, 2017. All rights reserved.
;;
;; This program and the accompanying materials
;; are made available under the terms of the Eclipse Public License v1.0
;; which accompanies this distribution, and is available at
;; http://www.eclipse.org/legal/epl-v10.html
;;


(ns csl-10-vector-public.core
  (:require [csl-10-vector-public.vector-of-mandelbrot :as vom])
  (:gen-class))

(defn -main [& args]
  (vom/show-image))


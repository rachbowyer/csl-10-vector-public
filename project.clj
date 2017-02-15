(defproject csl-10-vector-public "0.1.0-SNAPSHOT"
  :description "Example code for The Clojure Standard Library - Chaper 10 - Vectors"
  :dependencies [[org.clojure/clojure "1.9.0-alpha14"]            
                 [quil "2.5.0"]]
  :main ^:skip-aot csl-10-vector-public.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})


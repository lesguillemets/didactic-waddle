(defproject didactic-waddle "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [quil "2.4.0"]
                 [org.clojure/clojurescript "1.8.34"]]

  :plugins [[lein-cljsbuild "1.1.3"]]
  :hooks [leiningen.cljsbuild]

  :cljsbuild
  {:builds [{:source-paths ["src"]
             :compiler
             {:output-to "js/sketch00.js"
              :output-dir "outs/out00"
              :asset-path "../outs/out00"
              :main "didactic_waddle.sketch00"
              :optimizations :none
              :pretty-print true}},

            {:source-paths ["src"]
             :compiler
             {:output-to "js/sketch01.js"
              :output-dir "outs/out01"
              :asset-path "../outs/out01"
              :main "didactic_waddle.sketch01"
              :optimizations :none
              :pretty-print true}},

            {:source-paths ["src"]
             :compiler
             {:output-to "js/sketch02.js"
              :output-dir "outs/out02"
              :asset-path "../outs/out02"
              :main "didactic_waddle.sketch02"
              :optimizations :none
              :pretty-print true}},

            {:source-paths ["src"]
             :compiler
             {:output-to "js/sketch03.js"
              :asset-path "../outs/out03"
              :output-dir "outs/out03"
              :main "didactic_waddle.sketch03"
              :optimizations :none
              :pretty-print true}},

            {:source-paths ["src"]
             :compiler
             {:output-to "js/sketch04.js"
              :output-dir "outs/out04"
              :asset-path "../outs/out04"
              :main "didactic_waddle.sketch04"
              :optimizations :none
              :pretty-print true}},

            {:source-paths ["src"]
             :compiler
             {:output-to "js/sketch05.js"
              :output-dir "outs/out05"
              :asset-path "../outs/out05"
              :main "didactic_waddle.sketch05"
              :optimizations :none
              :pretty-print true}},
            ]})

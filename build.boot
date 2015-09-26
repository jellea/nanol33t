(set-env!
 :source-paths    #{"src/cljs"}
 :resource-paths  #{"resources"}
 :dependencies '[[adzerk/boot-cljs          "0.0-3308-0" :scope "test"]
                 [adzerk/boot-cljs-repl     "0.1.9"      :scope "test"]
                 [adzerk/boot-reload        "0.3.1"      :scope "test"]
                 [pandeiro/boot-http        "0.6.3"      :scope "test"]
                 [jeluard/boot-notify "0.2.0" :scope "test"]
                 [org.clojure/clojurescript "1.7.58"]
                 [reagent "0.5.0"]])

(require
 '[adzerk.boot-cljs      :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[adzerk.boot-reload    :refer [reload]]
 '[jeluard.boot-notify   :refer [notify]]
 '[pandeiro.boot-http    :refer [serve]])

(deftask build []
  (comp (notify)
        (cljs)))

(deftask run []
  (comp (serve)
        (watch)
        (cljs-repl)
        (reload)
        (build)))

(deftask production []
  (task-options! cljs {:optimizations :advanced})
  identity)

(deftask development []
  (task-options! cljs {:optimizations :none
                       :unified-mode true
                       :source-map true}
                 reload {:on-jsload 'nanol33t.app/init})
  identity)

(deftask dev
  "Simple alias to run application in development mode"
  []
  (comp (development)
        (run)))



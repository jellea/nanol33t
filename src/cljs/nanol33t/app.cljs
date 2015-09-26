(ns nanol33t.app
  (:require [reagent.core :as r]))

(enable-console-print!)

(defonce !global
         (r/atom {:tempo               120
                  :instrument-selected :a
                  :instruments         {:a {:selected-pattern 0
                                            :patterns         [{:pattern-length 16
                                                                :current-step   0
                                                                :pattern        [64 nil nil 64 64 nil nil 64 64 nil nil 64 64 nil nil 64]}]}
                                        :c {}
                                        :b {}}}))

(defn note-to-freq [note]
      (* (js/Math.pow 2 (/ (- note 69) 12) ) 440))

(defn step-view [index step]
  [:button {:key index} step])

(defn pattern-view []
  (let [selected-instrument (get-in @!global [:instruments (:instrument-selected @!global)])
        selected-pattern    (get-in selected-instrument [:patterns (:selected-pattern selected-instrument)])]
       [:div.pattern-view
        (map-indexed step-view (:pattern selected-pattern))]))

(defn instrument-selector [index instrument-name]
  [:button {:key index} (name (key instrument-name))])

(defn root-component []
  [:div
   [pattern-view]
   (map-indexed instrument-selector (:instruments @!global))])

(defn advance-step []
  (swap! !global #(update-in % [:instruments :a :patterns 0 :current-step] inc))
  (prn (get-in @!global [:instruments :a :patterns 0 :current-step])))

(js/setInterval advance-step 500)

(defn init []
  (r/render-component [root-component]
                            (.getElementById js/document "container")))

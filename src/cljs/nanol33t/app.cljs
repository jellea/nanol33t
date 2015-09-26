(ns nanol33t.app
  (:require [reagent.core :as r]))

(def !global (r/atom {:tempo       120
                     :instrument-selected :a
                     :instruments {:a {:selected-pattern 0
                                       :patterns [{:pattern-length 16
                                                   :pattern        [64 nil nil 64 64 nil nil 64 64 nil nil 64 64 nil nil 64]}]}
                                   :c {}
                                   :b {}}}))

(defn step-view []
  [:button "bla"])

(defn pattern-view []
  (let [selected-instrument (get-in @!global [:instruments (:instrument-selected @!global)])
        selected-pattern    (get-in selected-instrument [:patterns (:selected-pattern selected-instrument)])]
       (js/console.log selected-pattern)
       [:div
        (for [step (:pattern selected-pattern)]
             [step-view])]))

(defn instrument-selector [instrument-name]
  [:button (name (key instrument-name))])

(defn root-component []
  [:div
   [pattern-view]
   (for [x (:instruments @!global)]
        [instrument-selector x])])

(defn init []
  (r/render-component [root-component]
                            (.getElementById js/document "container")))

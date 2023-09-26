(ns stimulus.example.clipboard
  (:require [stimulus.controller :refer [->controller]]
            ["@hotwired/stimulus" :refer [Controller]]))

(defn write-to-clipboard [text]
  (.writeText (.-clipboard js/window.navigator) text))

(defn copy [controller event]
  (.preventDefault event)
  (let [value (.-value (get-source-target controller))]
    (write-to-clipboard value)))

(def controller (-> {:extends Controller
                     :targets ["source"]
                     :actions {:copy copy}}
                    ->controller))

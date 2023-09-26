(ns stimulus.example.example
  (:require [stimulus.controller :refer [->controller]]
            ["@hotwired/stimulus" :refer [Controller Application]]
            [clojure.browser.dom :as dom]))

(defn greet [controller event]
  (let [name-target (get-name-target controller)
        output-target (get-output-target controller)]
    (dom/set-text output-target (str "Hello, " (dom/get-value name-target)))))
  
(def greet-controller (-> {:extends Controller
                           :targets ["output" "name"]
                           :actions {:greet greet}}
                          ->controller))

(let [application (.start Application)]
  (.register application "hello" greet-controller))

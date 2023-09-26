(ns stimulus.example.application
  (:require ["@hotwired/stimulus" :refer [Application]]
            [stimulus.example.clipboard :as clipboard]
            [stimulus.example.greet :as greet]))

(let [application (.start Application)]
  (.register application "hello" greet/greet-controller)
  (.register application "clipboard" clipboard/controller))

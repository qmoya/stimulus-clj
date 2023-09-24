# Stimulus ❤️ ClojureScript
[![Clojars Project](https://img.shields.io/clojars/v/com.github.qmoya/stimulus.svg)](https://clojars.org/com.github.qmoya/stimulus)

[Stimulus](https://stimulus.hotwired.dev) is “a modest JavaScript framework for the HTML you already have.”
It was created by 37signals as an alternative to heavier approaches
to client-side interactions.

This is an experimental library that lets you write Stimulus controllers
using ClojureScript.

## Usage

An example Pedestal server:

```clojure
(ns my.example
  (:require [hiccup.page :as page]
            [io.pedestal.http :as http]
            [io.pedestal.http.route :as route])
  (:gen-class))

(defn home [req]
  {:status  200
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :body    (page/html5 [:head
                         [:title "Strada Demo"]
                         (page/include-js "/js/main.js")]
                        [:div {:data-controller "hello"}
                         [:input {:data-hello-target "name", :type "text"}]
                         [:button {:data-action "click->hello#greet"} "Hello"]
                         [:span {:data-hello-target "output"}]])})

(def routes
  (route/expand-routes
    #{["/" :get home :route-name :home]}))

(defn -main
  [& args]
  (-> {::http/routes         routes
       ::http/port           8082
       ::http/resource-path  "/public"
       ::http/secure-headers {:content-security-policy-settings {:object-src "none"}}
       ::http/type           :jetty}
      http/create-server
      http/start))
```

On the ClojureScript side:
```clojure
(ns my.client
  (:require [stimulus.core :refer [defcontroller] :as stimulus]
            ["@hotwired/stimulus" :refer [Controller Application]]
            [goog.dom :as gdom]))

(defcontroller ^{:extends Controller} GreetController
  {:targets ["output" "name"]}
  [(greet [this event]
          (let [output-target (.get-output-target this)
                name-target (.get-name-target this)
                name (.-value name-target)
                greeting (str "Hello, " name "!")]
            (gdom/setTextContent output-target greeting)))])

(let [application (.start Application)]
  (.register application "hello" strada/HelloController)
  (.register application "greet" GreetController))
```
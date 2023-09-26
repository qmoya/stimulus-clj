(ns stimulus.example.server
  (:require [hiccup.page :as page]
            [io.pedestal.http :as http]
            [io.pedestal.http.route :as route])
  (:gen-class))

(defn home [_]
  {:status  200
   :headers {"Content-Type" "text/html; charset=utf-8"}
   :body    (page/html5 [:head
                         [:title "Stimulus Examples"]
                         (page/include-js "/js/main.js")]
                        [:div {:data-controller "hello"}
                         [:input {:data-hello-target "name", :type "text"}]
                         [:button {:data-action "click->hello#greet"} "Greet"]
                         [:span {:data-hello-target "output"}]]
                        [:div {:data-controller "clipboard"} "PIN: "
                         [:input {:data-clipboard-target "source"
                                  :type "text"
                                  :value "1234"
                                  :readonly true}]
                         [:button {:data-action "clipboard#copy"} "Copy to Clipboard"]]
                        [:div {:data-controller "clipboard"} "PIN: "
                         [:textarea {:data-clipboard-target "source"
                                  :type "text"
                                  :value "ABCDEFG"
                                  :readonly true} "ABCDEFG"]
                         [:a {:href "#" :data-action "clipboard#copy"} "Copy to Clipboard"]])})

(def routes
  (route/expand-routes
   #{["/" :get home :route-name :home]}))

(defn -main
  [& _]
  (-> {::http/routes         routes
       ::http/port           3000
       ::http/resource-path  "/public"
       ::http/secure-headers {:content-security-policy-settings {:object-src "none"}}
       ::http/type           :jetty}
      http/create-server
      http/start))

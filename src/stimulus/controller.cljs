(ns stimulus.controller
  (:require ["@hotwired/stimulus" :refer [Application]]
            [clojure.string :as str]
            [camel-snake-kebab.core :as csk])
  (:require-macros [stimulus.controller :refer [defcontroller]]))

(defonce current-application (atom nil))

(defn- controller-name [keyword]
  (let [name (-> (name keyword)
                 (str/split #"/")
                 (last)
                 (str/split #"\.")
                 (first))]
    (symbol (csk/->PascalCase (str name "-controller")))))

(defn start [application]
  (let [app (.start Application)]
    (reset! current-application app)
    (doseq [[key controller] (:controllers application)]
      (let [c `(defcontroller ~(controller-name key) {:extends ~(:extends controller)} [])]
        (println c)))))

(defn stop [application]
  (.stop application))
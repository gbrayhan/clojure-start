(ns mi-api-rest.core
  (:require [migratus.core :as migratus]
            [mi-api-rest.routes :refer [app]]
            [ring.adapter.jetty :as jetty]
            [clojure.tools.cli :refer [parse-opts]]
            [clojure.string :as str])
  (:gen-class))

(defn read-config []
  (clojure.edn/read-string (slurp "resources/migratus.edn")))

(defn run-migrations []
  ;; Pasa la configuración directamente a Migratus
  (migratus/migrate (read-config)))

(defn rollback-migrations []
  ;; Pasa la configuración directamente a Migratus
  (migratus/rollback (read-config)))

(defn start-server []
  (jetty/run-jetty app {:port 3000 :join? false}))

(defn -main [& args]
  (let [{:keys [options arguments errors]} (parse-opts args [])]
    (cond
      (some #{"migrate"} args)
      (do
        (run-migrations)
        (System/exit 0))

      (some #{"rollback"} args)
      (do
        (rollback-migrations)
        (System/exit 0))

      :else
      (do
        (run-migrations)
        (start-server)))))

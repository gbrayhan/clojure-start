(defproject mi-api-rest "0.1.0-SNAPSHOT"
  :description "API REST en Clojure conectada a PostgreSQL"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [compojure "1.6.2"]
                 [ring/ring-defaults "0.3.3"]
                 [metosin/reitit "0.5.15"]
                 [org.postgresql/postgresql "42.3.1"]
                 [seancorfield/next.jdbc "1.4.794"]
                 [migratus "1.3.6"]
                 [cheshire "5.10.0"]]
  :main ^:skip-aot mi-api-rest.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :aliases {"migrate" ["run" "-m" "mi-api-rest.core" "migrate"]
            "rollback" ["run" "-m" "mi-api-rest.core" "rollback"]})

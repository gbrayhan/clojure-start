(defproject mi-api-rest "0.1.0-SNAPSHOT"
  :description "API REST en Clojure conectada a PostgreSQL"
  :repositories [["central" "https://repo1.maven.org/maven2/"]
                 ["clojars" "https://repo.clojars.org/"]]
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [compojure "1.6.2"]
                 [ring/ring-defaults "0.3.3"]
                 [metosin/reitit "0.5.15"]
                 [org.postgresql/postgresql "42.3.1"]
                 [seancorfield/next.jdbc "1.2.659"]
                 [migratus "1.3.6"]
                 [metosin/ring-http-response "0.9.4"]
                 [ring/ring-jetty-adapter "1.9.6"]
                 [org.clojure/tools.cli "1.0.206"]; <-- Añade esta línea
                 [cheshire "5.10.0"]]
  :uberjar-name "mi-api-rest.jar"  ;; Añade esta línea
  :main ^:skip-aot mi-api-rest.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :aliases {"migrate" ["run" "-m" "mi-api-rest.core" "migrate"]
            "rollback" ["run" "-m" "mi-api-rest.core" "rollback"]})

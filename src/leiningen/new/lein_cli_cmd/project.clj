(defproject {{ns-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.3.5"]]
  :main {{ns-name}}.core
  :aot [{{ns-name}}.core]
  :bin { :name "{{ns-name}}" }
  :plugins [[lein-bin "0.3.6-SNAPSHOT"]])

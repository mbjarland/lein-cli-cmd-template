(defproject {{ns-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/tools.cli "0.4.1"]]
  :main {{ns-name}}.core
  :aot [{{ns-name}}.core]
  :bin { :name "{{ns-name}}"
         :custom-preamble-script "boot/jar-preamble.sh" }
  :plugins [[lein-binplus "0.6.5"]]
  :profiles {:dev {:dependencies [[midje "1.9.6" :exclusions [org.clojure/clojure]]]
                   :plugins [[lein-midje "3.2.1"]]}})

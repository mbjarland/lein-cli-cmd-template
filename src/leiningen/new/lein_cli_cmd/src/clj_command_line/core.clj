(ns {{ns-name}}.core
    (:require [clojure.string :as string]
      [clojure.tools.cli :refer [parse-opts]])
    (:import (java.net InetAddress))
    (:gen-class))

(def cli-options
  [;; First three strings describe a short-option, long-option with optional
   ;; example argument description, and a description. All three are optional
   ;; and positional.
   ["-p" "--port PORT" "Port number"
    :default 80
    :parse-fn #(Integer/parseInt %)
    :validate [#(< 0 % 0x10000) "Must be a number between 0 and 65536"]]
   ["-H" "--hostname HOST" "Remote host"
    :default (InetAddress/getByName "localhost")
    ;; Specify a string to output in the default column in the options summary
    ;; if the default value's string representation is very ugly
    :default-desc "localhost"
    :parse-fn #(InetAddress/getByName %)]
   ;; If no required argument description is given, the option is assumed to
   ;; be a boolean option defaulting to nil
   [nil "--detach" "Detach from controlling process"]
   ["-v" nil "Verbosity level; may be specified multiple times to increase value"
    ;; If no long-option is specified, an option :id must be given
    :id :verbosity
    :default 0
    ;; Use assoc-fn to create non-idempotent options
    :assoc-fn (fn [m k _] (update-in m [k] inc))]
   ["-h" "--help"]])

(defn usage [options-summary]
      (->> ["This is my program. There are many like it, but this one is mine."
            ""
            "Usage: program-name [options] action"
            ""
            "Options:"
            options-summary
            ""
            "Actions:"
            "  start    Start a new server"
            "  stop     Stop an existing server"
            "  status   Print a server's status"
            ""
            "Please refer to the manual page for more information."]
           (string/join \newline)))

(defn error-msg [errors]
      (str "The following errors occurred while parsing your command:\n\n"
           (string/join \newline errors)))

(defn validate-args
      "Validate command line arguments. Either return a map indicating the program
      should exit (with a error message, and optional ok status), or a map
      indicating the action the program should take and the options provided."
      [args]
      (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
           (cond
             (:help options) {:exit-message (usage summary) :ok? true} ; help => exit OK with usage summary
             errors          {:exit-message (error-msg errors)}      ; errors => exit with description of errors
             (and (= 1 (count arguments))                   ; custom validation on arguments
                  (#{"start" "stop" "status"} (first arguments)))
                             {:action (first arguments) :options options}
             :else           {:exit-message (usage summary)})))       ; failed custom validation => exit with usage summary

(defn exit [status msg]
      (println msg)
      (System/exit status))

(defn -main [& args]
      (let [{:keys [action options exit-message ok?]} (validate-args args)]
           (if exit-message
             (exit (if ok? 0 1) exit-message)
             (case action
                   "start"  (println "starting server with: " options)
                   "stop"   (println "stopping server with: " options)
                   "status" (println "status   server with: " options)))))

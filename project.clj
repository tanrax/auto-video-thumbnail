(defproject video-optimize "1.0.0-SNAPSHOT"
  :description "Watcher and optimize videos"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 ;; Watcher
                 [hawk "0.2.11"]
                 ;; Yaml
                 [clj-yaml "0.4.0"]]
  :main ^:skip-aot video-optimize.core
  :aot  [video-optimize.core]
  :repl-options {:init-ns video-optimize.core})

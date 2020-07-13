(ns video-optimize.core
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]
   [hawk.core :as hawk]
   [clojure.java.shell :as shell]))

(def EXTENSION_THUMBNAIL "_thumbnail.mp4")
(def PATH_VIDEOS "videos")

(defn -main [& args]
  ;; Watch
  (hawk/watch! [{:paths   [PATH_VIDEOS]
                 :handler (fn [ctx e]
                            (let [path_raw       (.getAbsolutePath (:file e))
                                  is_thumbnail   (re-find (re-pattern EXTENSION_THUMBNAIL) path_raw)
                                  path_thumbnail (str/join (concat (drop-last (str/split path_raw #"\.")) EXTENSION_THUMBNAIL))]
                              (if (and (.exists (io/file path_raw)) (not is_thumbnail))
                                (do
                                  (prn (str "Optimizing: " path_raw))
                                  (shell/sh "ffmpeg" "-y" "-i" path_raw "-vf" "scale=600:-2" "-c:v" "libx264" "-crf" "23" "-profile:v" "high" "-pix_fmt" "yuv420p" "-color_primaries" "1" "-color_trc" "1" "-colorspace" "1" "-movflags" "+faststart" "-an" "-acodec" "aac" "-ab" "128kb" path_thumbnail)
                                  (prn (str "Finish: " path_thumbnail))))
                              )
                            )}])
  (println "Run video converter"))

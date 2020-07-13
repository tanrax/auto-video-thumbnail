# Usage

1) Create `config.yaml`.

``` bash
extension_thumbnail: "_thumbnail.mp4"
width_thumbnail: 600
path_videos: "videos"
```

2) Make folder `path_videos`.

``` bash
mkdir videos
```

3) Install `ffmpeg`.

4) Download the latest version (`video-optimize-{version}-standalone.jar`).

https://github.com/tanrax/auto-video-thumbnail/releases


5) Now you can execute.

``` bash
java $JVM_OPTS -cp video-optimize-{version}-standalone.jar clojure.main -m video-optimize.core
```

6) Leave videos in folder `videos`.

Everything you leave in the videos folder will be optimized for web with the specified resolution (600px in this example).

example.mp4 -> example_thumbnail.mp4

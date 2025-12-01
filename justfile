build:
    ./gradlew wasmJsBrowserDistribution

publish: build
    #!/usr/bin/env bash
    set -euo pipefail
    echo "====> deploying to github"
    # checkout the existing gh-pages
    rm -rf /tmp/gh-pages
    git worktree add -f /tmp/gh-pages gh-pages
    rm -rf /tmp/gh-pages/*
    # copy the build files to the gh-pages folder
    cp -rp ./composeApp/build/dist/wasmJs/productionExecutable/* /tmp/gh-pages/
    # push our new gh-pages
    cd /tmp/gh-pages && \
        git add -A && \
        git commit -m "deployed on $(shell date) by ${USER}" && \
        git push origin gh-pages
    git worktree remove /tmp/gh-pages
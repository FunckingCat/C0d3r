#-------------------------------------------------------------------------------------------------------------
# Copyright (c) Microsoft Corporation. All rights reserved.
# Licensed under the MIT License. See https://go.microsoft.com/fwlink/?linkid=2090316 for license information.
#-------------------------------------------------------------------------------------------------------------

FROM ubuntu:latest

ENV DEBIAN_FRONTEND=noninteractive \
    DEBCONF_NONINTERACTIVE_SEEN=true

RUN apt-get update \
    && apt-get -y install --no-install-recommends apt-utils 2>&1

# Verify git and needed tools are installed
RUN apt-get install --no-install-recommends -y git procps

# Install Tex Live
RUN apt-get update && apt-get -y upgrade \
    && apt-get -y install --no-install-recommends \
    texlive-latex-base \
    texlive-extra-utils \
    texlive-latex-extra \
    biber chktex latexmk make python3-pygments python3-pkg-resources 

# latexindent modules
RUN apt-get install --no-install-recommends -y curl
RUN curl -L http://cpanmin.us | perl - App::cpanminus \
    && cpanm Log::Dispatch::File \
    && cpanm YAML::Tiny \
    && cpanm File::HomeDir \
    && cpanm Unicode::GCString

# Configure debconf for ttf-mscorefonts-installer
RUN echo "ttf-mscorefonts-installer msttcorefonts/accepted-mscorefonts-eula select true" | debconf-set-selections

# Update package index and install necessary packages
RUN apt update && \
    apt install -y wget \
        git \
        make \
        apt-transport-https \
        unzip \
        texlive-full \
        biber \
        latexmk \
        fontconfig \
        ttf-mscorefonts-installer && \
    rm -rf /var/lib/apt/lists/*

# Download additional fonts
RUN wget -O /usr/share/fonts/xits-math.otf https://github.com/khaledhosny/xits-math/raw/master/XITSMath-Regular.otf && \
    wget https://ponce.cc/slackware/sources/repo/ttf-paratype-pt-fonts/PTSansOFL.zip && \
    wget https://ponce.cc/slackware/sources/repo/ttf-paratype-pt-fonts/PTMonoOFL.zip && \
    unzip -o PTSansOFL.zip -d /usr/share/fonts/ && \
    unzip -o PTMonoOFL.zip -d /usr/share/fonts/ && \
    rm -f PTSansOFL.zip PTMonoOFL.zip && \
    fc-cache -f -v

# Install tex-pscyr package
RUN wget https://people.debian.org/~sgolovan/debian/pool/main/tex/tex-pscyr/tex-pscyr_0.4d~beta9-1_all.deb && \
    dpkg -i tex-pscyr_0.4d~beta9-1_all.deb && \
    rm tex-pscyr_0.4d~beta9-1_all.deb

# Clean up
RUN apt-get autoremove -y \
    && apt-get clean -y \
    && rm -rf /var/lib/apt/lists/*
ENV DEBIAN_FRONTEND=dialog \
    LANG=C.UTF-8 \
    LC_ALL=C.UTF-8

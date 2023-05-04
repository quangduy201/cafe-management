#!/bin/bash

if [[ $(id -u) != 0 ]]; then
    echo "This script must be run as root. Please try again with sudo."
    exit 1
fi

installation_path=$(dirname "$0")
opencv_path="$installation_path/opencv-4.7.0"

if [[ ":$PATH:" == *":$opencv_path/bin:"* ]]; then
    echo "OpenCV bin folder is already in the environment variable."
else
    echo "Adding OpenCV bin folder to the environment variable..."
    echo "export PATH=\"\$PATH:$opencv_path/bin\"" >> /etc/profile.d/opencv.sh
    echo "OpenCV bin folder has been added to the environment variable."
    source /etc/profile.d/opencv.sh
fi

if [[ ":$LD_LIBRARY_PATH:" == *":$opencv_path/lib:"* ]]; then
    echo "OpenCV lib folder is already in the environment variable."
else
    echo "Adding OpenCV lib folder to the environment variable..."
    echo "export LD_LIBRARY_PATH=\"\$LD_LIBRARY_PATH:$opencv_path/lib\"" >> /etc/profile.d/opencv.sh
    echo "OpenCV lib folder has been added to the environment variable."
    source /etc/profile.d/opencv.sh
fi

sleep 3

# video-compressor

Compress videos before upload

## Install

```bash
npm install video-cmpression
npx cap sync
```

## Usage
#### Video
```bash
import { VideoCompressor,VideoQuality } from 'video-compressor-android';

 const compressVideo = async () => {
    const result = await VideoCompression.compressVideo({ inputVideoPath: '', videoQuality: VideoQuality.LOW, outputVideoName: "" });
  };

  VideoCompression.addListener('onProgress', (eventData: any) =>console.log(eventData));
```
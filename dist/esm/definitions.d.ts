import { Plugin } from "@capacitor/core";
export interface VideoCompressionPlugin extends Plugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    compressVideoUsingFFmpeg(options: VideoCompressionOptionFFmpeg): Promise<{
        value: string;
    }>;
}
export interface VideoCompressionOptionFFmpeg {
    inputVideoPath: string;
    outputVideoName: string;
}

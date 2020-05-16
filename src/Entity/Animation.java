package Entity;

import javafx.scene.image.Image;

public class Animation {

        private Image[] frames;
        private double duration;//desired frame change time
        private boolean played;
        private int currFrame;
        private double startTimeInSecond;
        public Animation()
        {
            played=false;
        }
        public void setFrames(Image[] images)
        {
            this.frames=images;//import frames
            currFrame=0;
            startTimeInSecond=System.currentTimeMillis()/1000;
            played=false;

        }

        public void setDuration(double d )
        {

            this.duration=d;
        }
        public void updateFrame()
        {
            double elapsed=(System.currentTimeMillis()/1000.0-startTimeInSecond);//second
            if(elapsed>duration)

            {
                currFrame++;
                startTimeInSecond=System.currentTimeMillis()/1000.0;//reset

            }
            if(currFrame==frames.length)
            {
                currFrame=0;
                played=true;
            }
        }
        public boolean playedOrNot()
        {
            return played;
        }
        public Image getImage()
        {
            return  frames[currFrame];
        }






























//        public Image getFrame(double time) {
//            int index = (int) ((time % (frames.length * duration)) / duration);
//            return frames[index];
//        }
//
//        public int getFrameIndex(double time) {
//            int index = (int) ((time % (frames.length * duration)) / duration);
//            return index;
//        }


}

package gxy.shanbaytest.tools;

/**
 * Created by Administrator on 2015/9/7.
 */
public class Capter {
    private static String html;
    public static String chooseCapter(int number){
        switch (number){
            case 1:
               html = "<html> \n" +
                        "<head> \n" +
                        "</head> \n" +
                        "<body>  \n" +
                        "<p align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;We can read of things that happened 5,000 years ago in the Near East, where people first learned to write. But there are some parts of the word where even now people cannot write. The only way that they can preserve their history is to recount it as sagas -- legends handed down from one generation of another. These legends are useful because they can tell us something about migrations of people who lived long ago, but none could write down what they did. Anthropologists wondered where the remote ancestors of the Polynesian peoples now living in the Pacific Islands came from. The sagas of these people explain that some of them came from Indonesia about 2,000 years ago.</p>\n" +
                        "<p align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;But the first people who were like ourselves lived so long ago that even their sagas, if they had any, are forgotten. So archaeologists have neither history nor legends to help them to find out where the first 'modern men' came from.</p> \n" +
                        "<p align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp; Fortunately, however, ancient men made tools of stone, especially flint, because this is easier to shape than other kinds. They may also have used wood and skins, but these have rotted away. Stone does not decay, and so the tools of long ago have remained when even the bones of the men who made them have disappeared without trace.</p> \n" +
                        "</body> \n" +
                        "</html> ";
                break;
            case 2:
                html = "<html> \n" +
                        "<head> \n" +
                        "</head> \n" +
                        "<body>  \n" +
                        "<p>&nbsp;&nbsp;&nbsp;&nbsp;Why, you may wonder, should spiders be our friends? Because they destroy so many insects, and insects include some of the greatest enemies of the human race. Insects would make it impossible for us to live in the world; they would devour all our crops and kill our flocks and herds, if it were not for the protection we get from insect-eating animals. We owe a lot to the birds and beasts who eat insects but all of them put together kill only a fraction of the number destroyed by spiders. Moreover, unlike some of the other insect eaters, spiders never do the harm to us or our belongings.</p>\n" +
                        "<p>&nbsp;&nbsp;&nbsp;&nbsp;Spiders are not insects, as many people think, nor even nearly related to them. One can tell the difference almost at a glance, for a spider always has eight legs and insect never more than six.</p>\n" +
                        " <p>&nbsp;&nbsp;&nbsp;&nbsp;How many spiders are engaged in this work no our behalf? One authority on spiders made a census of the spiders in grass field in the south of England, and he estimated that there were more than 2,250,000 in one acre; that is something like 6,000,000 spiders of different kinds on a football pitch. Spiders are busy for at least half the year in killing insects. It is impossible to make more than the wildest guess at how many they kill, but they are hungry creatures, not content with only three meals a day. It has been estimated that the weight of all the insects destroyed by spiders in Britain in one year would be greater than the total weight of all the human beings in the country.</p>\n" +
                        "</body> \n" +
                        "</html>  ";
                break;
            default:
                html = "null";
        }
        return html;
    }

}

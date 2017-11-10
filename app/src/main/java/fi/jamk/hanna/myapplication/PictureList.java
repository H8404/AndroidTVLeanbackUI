package fi.jamk.hanna.myapplication;

import java.util.ArrayList;
import java.util.List;

public final class PictureList {
    public static final String PICTURE_CATEGORY[] = {
            "Cat",
            "Landscape"
    };

    public static List<Picture> list;

    public static List<Picture> setupPictures() {
        list = new ArrayList<>();
        String title[] = {
                "Cat1",
                "Cat2",
                "Cat3",
                "Cat4",
                "Cat5",
                "Landscape1",
                "Landscape2",
                "Landscape3"
        };
        String bgImageUrl[] = {
                "https://cdn.pixabay.com/photo/2017/01/26/01/00/cat-2009498_960_720.jpg",
                "http://longwallpapers.com/Desktop-Wallpaper/space-cat-background-For-Desktop-Wallpaper.jpg",
                "https://pre00.deviantart.net/5044/th/pre/f/2014/178/f/e/taco_cat_in_space_by_jayro_jones-d7o5mow.jpg",
                "http://longwallpapers.com/Desktop-Wallpaper/space-cat-wallpapers-For-Desktop-Wallpaper.jpg",
                "https://cdn.wallpapersafari.com/71/28/BTmbrz.jpg",
                "http://data.freehdw.com/sunrise-landscapes-nature-trees-dawn-forests-hills-fog-mist-finland-hdr-photography-image-download.jpg",
                "https://www.picwallz.com/wp-content/uploads/2017/01/desktop-awesome-nature-landscape-project-gallery-with-pc-background-beautiful-hd-images-of-mobile-walpapers.jpg",
                "http://travelhdwallpapers.com/wp-content/uploads/2017/03/Lapland-Wallpaper-9.jpg"
        };

        list.add(buildPicture(PICTURE_CATEGORY[0], title[0], bgImageUrl[0]));
        list.add(buildPicture(PICTURE_CATEGORY[0], title[1], bgImageUrl[1]));
        list.add(buildPicture(PICTURE_CATEGORY[0], title[2], bgImageUrl[2]));
        list.add(buildPicture(PICTURE_CATEGORY[0], title[3], bgImageUrl[3]));
        list.add(buildPicture(PICTURE_CATEGORY[0], title[4], bgImageUrl[4]));
        list.add(buildPicture(PICTURE_CATEGORY[1], title[5], bgImageUrl[5]));
        list.add(buildPicture(PICTURE_CATEGORY[1], title[6], bgImageUrl[6]));
        list.add(buildPicture(PICTURE_CATEGORY[1], title[7], bgImageUrl[7]));

        return list;
    }

    private static Picture buildPicture(String category, String title, String bgImageUrl) {
        Picture pic = new Picture();
        pic.setId(Picture.getCount());
        Picture.incCount();
        pic.setTitle(title);
        pic.setCategory(category);
        pic.setBackgroundImageUrl(bgImageUrl);
        return pic;
    }
}

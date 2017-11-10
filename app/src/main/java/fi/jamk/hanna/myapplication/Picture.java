/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package fi.jamk.hanna.myapplication;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

public class Picture implements Serializable {
    private static long count = 0;
    private long id;
    private String title;
    private String bgImageUrl;
    private String category;

    public Picture() {
    }

    public static long getCount() {
        return count;
    }

    public static void incCount() {
        count++;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackgroundImageUrl() {
        return bgImageUrl;
    }

    public void setBackgroundImageUrl(String bgImageUrl) {
        this.bgImageUrl = bgImageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public URI getBackgroundImageURI() {
        try {
            return new URI(getBackgroundImageUrl());
        } catch (URISyntaxException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", backgroundImageUrl='" + bgImageUrl + '\'' +
                ", backgroundImageURI='" + getBackgroundImageURI().toString() + '\'' +
                '}';
    }
}

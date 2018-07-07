package com.example.blnsft.models;

import com.google.gson.annotations.SerializedName;

public class AccountResponseOk {
        private String status;

        @SerializedName("data")
        private UserData userData;

        public String getStatus ()
        {
            return status;
        }

        public void setStatus (String status)
        {
            this.status = status;
        }

        public UserData getUserData()
        {
            return userData;
        }

        public void setUserData(UserData data)
        {
            this.userData = data;
        }

        @Override
        public String toString()
        {
            return "ClassPojo [status = "+status+", data = "+ userData.toString()+"]";
        }
}

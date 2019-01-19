package com.fishpond.smartapp.bean;


import com.google.gson.annotations.SerializedName;

/**
 * Created by zhouh on 2019/1/6.
 */
public class LocationBean {
    private String status;
    private LocationResult result;

    public void setResult(LocationResult result) {
        this.result = result;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocationResult getResult() {
        return result;
    }

    public String getStatus() {
        return status;
    }

    public class LocationInfo {
        private String lng;
        private String lat;

        public void setLat(String lat) {
            this.lat = lat;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public String getLng() {
            return lng;
        }
    }

    public class LocationResult {
        private LocationInfo location;
        private String formatted_address;
        private String business;
        private int cityCode;
        private AddressComponent addressComponent;

        public void setAddressComponent(AddressComponent addressComponent) {
            this.addressComponent = addressComponent;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public void setCityCode(int cityCode) {
            this.cityCode = cityCode;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public void setLocation(LocationInfo location) {
            this.location = location;
        }

        public AddressComponent getAddressComponent() {
            return addressComponent;
        }

        public int getCityCode() {
            return cityCode;
        }

        public LocationInfo getLocation() {
            return location;
        }

        public String getBusiness() {
            return business;
        }

        public String getFormatted_address() {
            return formatted_address;
        }
    }

    public class AddressComponent {
        @SerializedName("city")
        private String mCity;
        private String direction;
        private String distance;
        private String district;
        private String province;
        private String street;
        private String street_number;

        public void setCity(String city) {
            this.mCity = city;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public void setStreet_number(String street_number) {
            this.street_number = street_number;
        }

        public String getCity() {
            return mCity;
        }

        public String getDirection() {
            return direction;
        }

        public String getDistance() {
            return distance;
        }

        public String getDistrict() {
            return district;
        }

        public String getProvince() {
            return province;
        }

        public String getStreet() {
            return street;
        }

        public String getStreet_number() {
            return street_number;
        }
    }
}

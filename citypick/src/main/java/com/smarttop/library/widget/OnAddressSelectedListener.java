package com.smarttop.library.widget;


import com.smarttop.library.bean.City;
import com.smarttop.library.bean.District;
import com.smarttop.library.bean.Province;
import com.smarttop.library.bean.Street;

public interface OnAddressSelectedListener {
    void onAddressSelected(Province province, City city, District district, Street street);
}

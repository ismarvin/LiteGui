package com.rainchat.raingui.utils.placeholder;

public interface PlaceholderSupply<T> {
    Class<T> forClass();

    String getReplacement(String forKey);
}

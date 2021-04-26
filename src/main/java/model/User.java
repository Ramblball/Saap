package model;

import com.google.gson.annotations.SerializedName;
import lombok.NonNull;
import lombok.Value;

@Value
public class User implements Entity{
    @SerializedName(ModelLiterals.ID)
    @NonNull
    String id;

    @NonNull
    String name;

    @NonNull
    Double age;
}

package dev.netanelbcn.kinderkit.Interfaces;


import java.util.List;

import dev.netanelbcn.kinderkit.ExternalModels.Bounderies.MiniAppCommandBoundary;
import dev.netanelbcn.kinderkit.ExternalModels.Bounderies.ObjectBoundary;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EventService {

    @POST("/superapp/miniapp/{miniAppName}")
    Call<List<Object>> loadAllBabysittingEvents(@Path("miniAppName") String miniAppName,
                                                @Body MiniAppCommandBoundary commandBoundary);

    @POST("/superapp/objects")
    Call<ObjectBoundary> createEvent(@Body ObjectBoundary boundaryObject);


}

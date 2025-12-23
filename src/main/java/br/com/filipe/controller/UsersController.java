package br.com.filipe.controller;

import br.com.filipe.Utils.GenerateToken;
import br.com.filipe.Utils.RoleEnum;
import br.com.filipe.model.UserModel;
import br.com.filipe.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.UUID;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersController {

    private UserService userService;
    private GenerateToken generateToken = new GenerateToken();

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Path("/token")
    public Response getToken(){
        String token = generateToken.generateJwt(RoleEnum.admin);

        return Response.ok(token).build();
    }

    @GET
    @RolesAllowed("user")
    public Response listAll(@QueryParam("page") @DefaultValue("0") Integer page,
                            @QueryParam("pageSize") @DefaultValue("5") Integer pageSize){
        List<UserModel> users = userService.listUsers(page, pageSize);

        return Response.ok(users).build();
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") UUID id){
        return Response.ok(userService.findById(id)).build();
    }

    @POST
    @Transactional
    public Response createUser(UserModel userModel){
        return Response.ok(userService.createUser(userModel)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateUser(@PathParam("id") UUID id, UserModel userModel){
        return Response.ok(userService.updateUser(id, userModel)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") UUID id){
        userService.deleteUser(id);
        return Response.noContent().build();
    }
}

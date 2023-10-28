package project.fashionecommerce.backend.fashionecommerceproject.controller.admin.follow;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.follow.models.FollowModelMapper;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.follow.models.FollowRequest;
import project.fashionecommerce.backend.fashionecommerceproject.controller.admin.follow.models.FollowResponse;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.Follow;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.FollowId;
import project.fashionecommerce.backend.fashionecommerceproject.service.database.follow.FollowUseCaseService;

@RestController
@RequiredArgsConstructor
public class FollowController implements FollowAPI {
    @NonNull
    final FollowUseCaseService followUseCaseService;
    @NonNull
    final FollowModelMapper followModelMapper;
    @Override
    public void update(String followId, FollowRequest followRequest) {
        Follow follow = followModelMapper.toDto(followRequest);
        followUseCaseService.update(new FollowId(followId), follow);
    }

    @Override
    public ResponseEntity<FollowResponse> findById(String followId) {
        Follow follow = followUseCaseService.findById(new FollowId(followId));
        FollowResponse followResponse = followModelMapper.toModel(follow);
        return new ResponseEntity<>(followResponse, HttpStatus.OK);
    }

    @Override
    public void delete(String followId) {
        followUseCaseService.delete(new FollowId(followId));
    }
}

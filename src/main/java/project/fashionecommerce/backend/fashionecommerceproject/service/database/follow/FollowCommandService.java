package project.fashionecommerce.backend.fashionecommerceproject.service.database.follow;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.Follow;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.FollowId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.FollowMapper;
import project.fashionecommerce.backend.fashionecommerceproject.exception.MyResourceNotFoundException;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.follow.FollowEntity;
import project.fashionecommerce.backend.fashionecommerceproject.repository.database.follow.FollowRepository;

@Service
@RequiredArgsConstructor
public class FollowCommandService {
    @NonNull
    final FollowRepository followRepository;
    @NonNull
    final FollowMapper followMapper;

    public void save(Follow follow) {
        FollowEntity followEntity = followMapper.toEntity(follow);
        followRepository.save(followEntity);
    }

    public void update(FollowId followId, Follow follow) {
        FollowEntity foundEntity = followRepository.findById(followId.id()).orElseThrow(MyResourceNotFoundException::new);
        followMapper.updateExisted(follow, foundEntity);
        followRepository.save(foundEntity);
    }

    public void delete(FollowId followId) {
        followRepository.deleteById(followId.id());
    }
}

package project.fashionecommerce.backend.fashionecommerceproject.service.database.follow;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.fashionecommerce.backend.fashionecommerceproject.dto.enums.ERole;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.Follow;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.FollowId;
import project.fashionecommerce.backend.fashionecommerceproject.dto.follow.FollowQuery;

@Service
@RequiredArgsConstructor
public class FollowUseCaseService {
    @NonNull
    final FollowCommandService followCommandService;
    @NonNull
    final FollowQueryService followQueryService;
    @Transactional
    public void save(Follow follow) {
        followCommandService.save(follow);
    }

    @Transactional
    public void update(FollowId followId, Follow follow) {
        followCommandService.update(followId, follow);
    }
    @Transactional
    public void delete(FollowId followId) {
        followCommandService.delete(followId);
    }

    public Follow findById(FollowId followId) {
        return followQueryService.findById(followId);
    }

    public Page<Follow> findAll(FollowQuery followQuery, PageRequest pageRequest) {
        return followQueryService.findAll(followQuery, pageRequest);
    }
}

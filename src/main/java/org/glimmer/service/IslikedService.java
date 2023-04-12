package org.glimmer.service;

public interface IslikedService {

    public boolean isLiked(Long fileId,Long userId);

    public boolean isDisliked(Long fileId,Long userId);
}

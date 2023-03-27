package com.example.bookshopapp.data.genre.genreTree;

import com.example.bookshopapp.data.genre.GenreEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
public class GenreNode {
    private GenreEntity genre;
    private int parentId;
    private int amountBook;
    private Set<GenreNode> children;

    private int nestingLevel;

    public GenreNode(GenreEntity genre, int amountBook) {
        this.genre = genre;
        this.parentId = genre.getParentId() == null ? 0 : genre.getParentId();
        this.children = new HashSet<>();
        this.amountBook = amountBook;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "genre=" + genre +
                ", parent_id=" + parentId +
                ", children=" + children +
                '}';
    }

    public void addChildren(GenreNode parentNode, List<GenreNode> genreNodes, int nestingLevel) {
        nestingLevel++;
        for (GenreNode genreNode : genreNodes) {
            parentNode.setNestingLevel(nestingLevel);
            if (genreNode.getParentId() == parentNode.getGenre().getId()) {
                parentNode.getChildren().add(genreNode);
                addChildren(genreNode, genreNodes, nestingLevel);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreNode genreNode = (GenreNode) o;
        return Objects.equals(genre, genreNode.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genre);
    }
}

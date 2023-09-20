job('First-Java-Compilation-DSL'){
    description("First Maven job generated bt the DSL on ${new Date()}")
    scm{
        git("git@github.com:enumahin/spring-webflux-testing.git", master)
    }
    trigger{
        scm("* * * * *")
    }
    steps{
        maven("clean package")
    }
    publishers {
        // archive the war file generated
        archiveArtifacts('../*.jar')
    }
}